package com.my.company.codegen;

import com.google.common.collect.ImmutableMap;
import com.samskivert.mustache.Mustache;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.ComposedSchema;
import io.swagger.v3.parser.util.SchemaTypeUtil;
import org.openapitools.codegen.*;
import org.openapitools.codegen.meta.features.DocumentationFeature;
import org.openapitools.codegen.model.ModelMap;
import org.openapitools.codegen.model.ModelsMap;
import org.openapitools.codegen.model.OperationMap;
import org.openapitools.codegen.model.OperationsMap;
import org.openapitools.codegen.templating.mustache.IndentedLambda;
import org.openapitools.codegen.utils.ModelUtils;
import org.openapitools.codegen.CodegenConstants.ENUM_PROPERTY_NAMING_TYPE;
import org.openapitools.codegen.CodegenConstants.MODEL_PROPERTY_NAMING_TYPE;
import org.openapitools.codegen.CodegenConstants.PARAM_NAMING_TYPE;
import org.openapitools.codegen.meta.features.*;

import org.openapitools.codegen.languages.AbstractTypeScriptClientCodegen;

import java.io.File;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.openapitools.codegen.utils.StringUtils.camelize;
import static org.openapitools.codegen.utils.StringUtils.underscore;

import static org.openapitools.codegen.utils.OnceLogger.once;


public class MyCodegenGenerator extends AbstractTypeScriptClientCodegen {

  // source folder where to write the files
  protected String sourceFolder = ".";
  protected String apiVersion = "1.0.0";


  protected boolean addedApiIndex = false;
  protected boolean addedModelIndex = false;

  /**
   * Configures the type of generator.
   *
   * @return  the CodegenType for this generator
   * @see     org.openapitools.codegen.CodegenType
   */
  public CodegenType getTag() {
    return CodegenType.OTHER;
  }

  /**
   * Configures a friendly name for the generator.  This will be used by the generator
   * to select the library with the -g flag.
   *
   * @return the friendly name for the generator
   */
  public String getName() {
    return "my-codegen";
  }

  /**
    * Converts a list of strings to a literal union for representing enum values as a type.
    * Example output: 'available' | 'pending' | 'sold'
    *
    * @param values   list of allowed enum values
    * @param dataType either "string" or "number"
    * @return a literal union for representing enum values as a type
    */
  protected String enumValuesToEnumTypeUnion(List<String> values, String dataType) {
      StringBuilder b = new StringBuilder();
      boolean isFirst = true;
      for (String value : values) {
          if (!isFirst) {
              b.append(" | ");
          }
          b.append(toEnumValue(value, dataType));
          isFirst = false;
      }
      return b.toString();
  }

    /**
      * Converts a list of numbers to a literal union for representing enum values as a type.
      * Example output: 3 | 9 | 55
      *
      * @param values a list of numbers
      * @return a literal union for representing enum values as a type
      */
    protected String numericEnumValuesToEnumTypeUnion(List<Number> values) {
        List<String> stringValues = new ArrayList<>();
        for (Number value : values) {
            stringValues.add(value.toString());
        }
        return enumValuesToEnumTypeUnion(stringValues, "number");
    }

    @Override
    public String getTypeDeclaration(Schema p) {
        Schema inner;
        if (ModelUtils.isArraySchema(p)) {
            inner = ((ArraySchema) p).getItems();
            // return this.getSchemaType(p) + "<" + this.getTypeDeclaration(unaliasSchema(inner)) + ">";
            return this.getSchemaType(p) + "<" + this.getTypeDeclaration(inner) + ">";
        } else if (ModelUtils.isMapSchema(p)) {
            inner = getSchemaAdditionalProperties(p);
            // return "{ [key: string]: " + this.getTypeDeclaration(unaliasSchema(inner)) + "; }";
            return "{ [key: string]: " + this.getTypeDeclaration(inner) + "; }";
        } else if (ModelUtils.isFileSchema(p)) {
            return "HttpFile";
        } else if (ModelUtils.isBinarySchema(p)) {
            return "any";
        } else {
            return super.getTypeDeclaration(p);
        }
    }

    @Override
    protected String getParameterDataType(Parameter parameter, Schema p) {

        // handle enums of various data types
        Schema inner;
        if (ModelUtils.isArraySchema(p)) {
            ArraySchema mp1 = (ArraySchema) p;
            inner = mp1.getItems();
            return this.getSchemaType(p) + "<" + this.getParameterDataType(parameter, inner) + ">";
        } else if (ModelUtils.isMapSchema(p)) {
            inner = getAdditionalProperties(p);
            return "{ [key: string]: " + this.getParameterDataType(parameter, inner) + "; }";
        } else if (ModelUtils.isStringSchema(p)) {
            // Handle string enums
            if (p.getEnum() != null) {
                return enumValuesToEnumTypeUnion(p.getEnum(), "string");
            } else {
              return "string";
            }
        } else if (ModelUtils.isIntegerSchema(p)) {
            // Handle integer enums
            if (p.getEnum() != null) {
                return numericEnumValuesToEnumTypeUnion(new ArrayList<Number>(p.getEnum()));
            }
        } else if (ModelUtils.isNumberSchema(p)) {
            // Handle double enums
            if (p.getEnum() != null) {
                return numericEnumValuesToEnumTypeUnion(new ArrayList<Number>(p.getEnum()));
            }
        }
        if (ModelUtils.isStringSchema(p)) {
          return "string";
        }
        return this.getTypeDeclaration(p);
    }

  /**
   * Provides an opportunity to inspect and modify operation data before the code is generated.
   */
  // @Override
  // public OperationsMap postProcessOperationsWithModels(OperationsMap objs, List<ModelMap> allModels) {

  //   // to try debugging your code generator:
  //   // set a break point on the next line.
  //   // then debug the JUnit test called LaunchGeneratorInDebugger

  //   OperationsMap results = super.postProcessOperationsWithModels(objs, allModels);

  //   OperationMap ops = results.getOperations();
  //   List<CodegenOperation> opList = ops.getOperation();

  //   // iterate over the operation and perhaps modify something
  //   for(CodegenOperation co : opList){
  //     // example:
  //     // co.httpMethod = co.httpMethod.toLowerCase();
  //   }

  //   return results;
  // }

  /**
   * Returns human-friendly help for the generator.  Provide the consumer with help
   * tips, parameters here
   *
   * @return A string value for the help message
   */
  public String getHelp() {
    return "Generates a my-codegen client library.";
  }

  public MyCodegenGenerator() {
    super();

    // set the output folder here
    outputFolder = "generated-code/my-codegen";

    supportModelPropertyNaming(CodegenConstants.MODEL_PROPERTY_NAMING_TYPE.camelCase);

    // clear import mapping (from default generator) as TS does not use it
    // at the moment
    importMapping.clear();


    modelTemplateFiles.put(
      "models.mustache", // the template to use
      ".ts");       // the extension for each file to write

    apiTemplateFiles.put(
      "api.mustache",   // the template to use
      ".ts");       // the extension for each file to write


    templateDir = "my-codegen";
    apiPackage = "api";
    modelPackage = "models";

    additionalProperties.put("apiVersion", apiVersion);

    supportingFiles.add(new SupportingFile("common.mustache",
      "",
      "common.ts")
    );

    supportingFiles.add(new SupportingFile("index.mustache",
      "",
      "index.ts")
    );

    /**
     * Language Specific Primitives.  These types will not trigger imports by
     * the client generator
     */
     // these will keep primatives out of model imports, probably a better way
    languageSpecificPrimitives = new HashSet<String>(
      Arrays.asList(
        "any",      // replace these with your types
        "number",
        "Array")
    );

    this.addExtraReservedWords();
  }

  /**
   * Escapes a reserved word as defined in the `reservedWords` array. Handle escaping
   * those terms here.  This logic is only called if a variable matches the reserved words
   *
   * @return the escaped term
   */
  @Override
  public String escapeReservedWord(String name) {
    return "_" + name;  // add an underscore to the name
  }

  @Override
  protected void addAdditionPropertiesToCodeGenModel(CodegenModel codegenModel, Schema schema) {
      codegenModel.additionalPropertiesType = getTypeDeclaration(getAdditionalProperties(schema));
      addImport(codegenModel, codegenModel.additionalPropertiesType);
  }

  /**
   * Location to write model files.  You can use the modelPackage() as defined when the class is
   * instantiated
   */
  public String modelFileFolder() {
    return outputFolder + "/" + modelPackage().replace('.', File.separatorChar);
  }

  /**
   * Location to write api files.  You can use the apiPackage() as defined when the class is
   * instantiated
   */
  @Override
  public String apiFileFolder() {
    return outputFolder + "/" + apiPackage().replace('.', File.separatorChar);
  }

  @Override
  public OperationsMap postProcessOperationsWithModels(OperationsMap operations, List<ModelMap> allModels) {
    // Add supporting file only if we plan to generate files in /apis
    if (!operations.isEmpty() && !addedApiIndex) {
        addedApiIndex = true;
        supportingFiles.add(new SupportingFile("apis.index.mustache", apiPackage, "index.ts"));
    }

    // Add supporting file only if we plan to generate files in /models
    if (!allModels.isEmpty() && !addedModelIndex) {
        addedModelIndex = true;
        supportingFiles.add(new SupportingFile("models.index.mustache", modelPackage, "index.ts"));
    }

    OperationMap ops = operations.getOperations();
    List<CodegenOperation> opList = ops.getOperation();
    for(CodegenOperation co : opList){
      // example:
      co.httpMethod = co.httpMethod.toLowerCase();
    }

    this.addOperationModelImportInformation(operations);
    this.updateOperationParameterEnumInformation(operations);
    this.addOperationObjectResponseInformation(operations);
    this.addOperationPrefixParameterInterfacesInformation(operations);
    // this.escapeOperationIds(operations);
    return operations;
  }

  private void addOperationModelImportInformation(OperationsMap operations) {
    // This method will add extra information to the operations.imports array.
    // The api template uses this information to import all the required
    // models for a given operation.
    List<Map<String, String>> imports = operations.getImports();

    List<String> existingRecordClassNames = new ArrayList<>();
    List<String> existingClassNames = new ArrayList<>();
    for (Map<String, String> im : imports) {
        String className = im.get("import").replace(modelPackage() + ".", "");
        existingClassNames.add(className);
        existingRecordClassNames.add(className + "Record");
        im.put("className", className);
    }
  }

  private void updateOperationParameterEnumInformation(Map<String, Object> operations) {
    // This method will add extra infomation as to whether or not we have enums and
    // update their names with the operation.id prefixed.
    Map<String, Object> _operations = (Map<String, Object>) operations.get("operations");
    List<CodegenOperation> operationList = (List<CodegenOperation>) _operations.get("operation");
    boolean hasEnum = false;
    for (CodegenOperation op : operationList) {
        for (CodegenParameter param : op.allParams) {
            if (Boolean.TRUE.equals(param.isEnum)) {
                hasEnum = true;
                param.datatypeWithEnum = param.datatypeWithEnum
                        .replace(param.enumName, op.operationIdCamelCase + param.enumName);
            }
        }
    }

    operations.put("hasEnums", hasEnum);
  }

  private void addOperationObjectResponseInformation(Map<String, Object> operations) {
    // This method will modify the infomation on the operations' return type.
    // The api template uses this infomation to know when to return a text
    // response for a given simple response operation.
    Map<String, Object> _operations = (Map<String, Object>) operations.get("operations");
    List<CodegenOperation> operationList = (List<CodegenOperation>) _operations.get("operation");
    for (CodegenOperation op : operationList) {
        if(op.returnType == "object") {
            // op.isMapContainer = true;
            // op.returnSimpleType = false;
        }
    }
  }

  private void addOperationPrefixParameterInterfacesInformation(Map<String, Object> operations) {
    Map<String, Object> _operations = (Map<String, Object>) operations.get("operations");
    // operations.put("prefixParameterInterfaces", getPrefixParameterInterfaces());
  }

  // private void addOperationObjectResponseInformation(OperationsMap operations) {
  //   // This method will modify the information on the operations' return type.
  //   // The api template uses this information to know when to return a text
  //   // response for a given simple response operation.
  //   for (CodegenOperation _op : operations.getOperations().getOperation()) {
  //       ExtendedCodegenOperation op = (ExtendedCodegenOperation) _op;
  //       if ("object".equals(op.returnType)) {
  //           op.isMap = true;
  //           op.returnSimpleType = false;
  //       }
  //   }
  // }

  private void addExtraReservedWords() {
    this.reservedWords.add("BASE_PATH");
    this.reservedWords.add("BaseAPI");
    this.reservedWords.add("RequiredError");
    this.reservedWords.add("COLLECTION_FORMATS");
    this.reservedWords.add("FetchAPI");
    this.reservedWords.add("ConfigurationParameters");
    this.reservedWords.add("Configuration");
    this.reservedWords.add("configuration");
    this.reservedWords.add("HTTPMethod");
    this.reservedWords.add("HTTPHeaders");
    this.reservedWords.add("HTTPQuery");
    this.reservedWords.add("HTTPBody");
    this.reservedWords.add("ModelPropertyNaming");
    this.reservedWords.add("FetchParams");
    this.reservedWords.add("RequestOpts");
    this.reservedWords.add("exists");
    this.reservedWords.add("RequestContext");
    this.reservedWords.add("ResponseContext");
    this.reservedWords.add("Middleware");
    this.reservedWords.add("ApiResponse");
    this.reservedWords.add("ResponseTransformer");
    this.reservedWords.add("JSONApiResponse");
    this.reservedWords.add("VoidApiResponse");
    this.reservedWords.add("BlobApiResponse");
    this.reservedWords.add("TextApiResponse");
    // "Index" would create a file "Index.ts" which on case insensitive filesystems
    // would override our "index.js" file
    this.reservedWords.add("Index");
  }

  /**
   * override with any special text escaping logic to handle unsafe
   * characters so as to avoid code injection
   *
   * @param input String to be cleaned up
   * @return string with unsafe characters removed or escaped
   */
  @Override
  public String escapeUnsafeCharacters(String input) {
    //TODO: check that this logic is safe to escape unsafe characters to avoid code injection
    return input;
  }

  /**
   * Escape single and/or double quote to avoid code injection
   *
   * @param input String to be cleaned up
   * @return string with quotation mark removed or escaped
   */
  public String escapeQuotationMark(String input) {
    //TODO: check that this logic is safe to escape quotation mark to avoid code injection
    return input.replace("\"", "\\\"");
  }
}
