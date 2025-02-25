#' Create a new Zebra
#'
#' @description
#' Zebra Class
#'
#' @docType class
#' @title Zebra
#' @description Zebra Class
#' @format An \code{R6Class} generator object
#' @field type  character [optional]
#' @field className  character
#' @importFrom R6 R6Class
#' @importFrom jsonlite fromJSON toJSON
#' @export
Zebra <- R6::R6Class(
  "Zebra",
  public = list(
    `type` = NULL,
    `className` = NULL,
    #' Initialize a new Zebra class.
    #'
    #' @description
    #' Initialize a new Zebra class.
    #'
    #' @param className className
    #' @param type type
    #' @param ... Other optional arguments.
    #' @export
    initialize = function(
        `className`, `type` = NULL, ...
    ) {
      if (!missing(`className`)) {
        stopifnot(is.character(`className`), length(`className`) == 1)
        self$`className` <- `className`
      }
      if (!is.null(`type`)) {
        stopifnot(is.character(`type`), length(`type`) == 1)
        self$`type` <- `type`
      }
    },
    #' To JSON string
    #'
    #' @description
    #' To JSON String
    #'
    #' @return Zebra in JSON format
    #' @export
    toJSON = function() {
      ZebraObject <- list()
      if (!is.null(self$`type`)) {
        ZebraObject[["type"]] <-
          self$`type`
      }
      if (!is.null(self$`className`)) {
        ZebraObject[["className"]] <-
          self$`className`
      }

      ZebraObject
    },
    #' Deserialize JSON string into an instance of Zebra
    #'
    #' @description
    #' Deserialize JSON string into an instance of Zebra
    #'
    #' @param input_json the JSON input
    #' @return the instance of Zebra
    #' @export
    fromJSON = function(input_json) {
      this_object <- jsonlite::fromJSON(input_json)
      if (!is.null(this_object$`type`)) {
        self$`type` <- this_object$`type`
      }
      if (!is.null(this_object$`className`)) {
        self$`className` <- this_object$`className`
      }
      self
    },
    #' To JSON string
    #'
    #' @description
    #' To JSON String
    #'
    #' @return Zebra in JSON format
    #' @export
    toJSONString = function() {
      jsoncontent <- c(
        if (!is.null(self$`type`)) {
          sprintf(
          '"type":
            "%s"
                    ',
          self$`type`
          )
        },
        if (!is.null(self$`className`)) {
          sprintf(
          '"className":
            "%s"
                    ',
          self$`className`
          )
        }
      )
      jsoncontent <- paste(jsoncontent, collapse = ",")
      as.character(jsonlite::minify(paste("{", jsoncontent, "}", sep = "")))
    },
    #' Deserialize JSON string into an instance of Zebra
    #'
    #' @description
    #' Deserialize JSON string into an instance of Zebra
    #'
    #' @param input_json the JSON input
    #' @return the instance of Zebra
    #' @export
    fromJSONString = function(input_json) {
      this_object <- jsonlite::fromJSON(input_json)
      self$`type` <- this_object$`type`
      self$`className` <- this_object$`className`
      self
    },
    #' Validate JSON input with respect to Zebra
    #'
    #' @description
    #' Validate JSON input with respect to Zebra and throw an exception if invalid
    #'
    #' @param input the JSON input
    #' @export
    validateJSON = function(input) {
      input_json <- jsonlite::fromJSON(input)
      # check the required field `className`
      if (!is.null(input_json$`className`)) {
        stopifnot(is.character(input_json$`className`), length(input_json$`className`) == 1)
      } else {
        stop(paste("The JSON input `", input, "` is invalid for Zebra: the required field `className` is missing."))
      }
    },
    #' To string (JSON format)
    #'
    #' @description
    #' To string (JSON format)
    #'
    #' @return String representation of Zebra
    #' @export
    toString = function() {
      self$toJSONString()
    },
    #' Return true if the values in all fields are valid.
    #'
    #' @description
    #' Return true if the values in all fields are valid.
    #'
    #' @return true if the values in all fields are valid.
    #' @export
    isValid = function() {
      # check if the required `className` is null
      if (is.null(self$`className`)) {
        return(FALSE)
      }

      TRUE
    },
    #' Return a list of invalid fields (if any).
    #'
    #' @description
    #' Return a list of invalid fields (if any).
    #'
    #' @return A list of invalid fields (if any).
    #' @export
    getInvalidFields = function() {
      invalid_fields <- list()
      # check if the required `className` is null
      if (is.null(self$`className`)) {
        invalid_fields["className"] <- "Non-nullable required field `className` cannot be null."
      }

      invalid_fields
    }
  ),
  # Lock the class to prevent modifications to the method or field
  lock_class = TRUE
)

# Unlock the class to allow modifications of the method or field
Zebra$unlock()

#' Print the object
#'
#' @description
#' Print the object
#'
#' @export
Zebra$set("public", "print", function(...) {
  print(jsonlite::prettify(self$toJSONString()))
  invisible(self)
})

# Lock the class to prevent modifications to the method or field
Zebra$lock()

