import { plainToInstance } from "class-transformer";
import { validate, ValidationError } from "class-validator";
import { NextFunction, Request, Response, RequestHandler } from "express";

class HttpException extends Error {
  public status: number;
  public message: string;

  constructor(status: number, message: string) {
    super(message);
    this.status = status;
    this.message = message;
  }
}

const validationMiddleware = (
  validations: {
    body?: any,
    query?: any,
    params?: any
  },
  skipMissingProperties = false,
  whitelist = true,
  forbidNonWhitelisted = true
): RequestHandler => {
  return (req, res, next) => {
    const validationPromises  = Object.keys(validations).filter((key) => { return validations[key] !== undefined }).map((key) => {
      return validate(plainToInstance(validations[key], req[key]), {
        skipMissingProperties,
        whitelist,
        forbidNonWhitelisted,
      })
    });

    Promise.all(validationPromises).then((errors: ValidationError[][]) => {
      const flattenedErrors = errors.flat();
      if (flattenedErrors.length > 0) {
        const message = flattenedErrors
          .map((error: ValidationError) => Object.values(error.constraints))
          .join(", ");
        next(new HttpException(400, message));
      } else {
        next();
      }
    });
  };
};

const errorMiddleware = (
  error: HttpException,
  req: Request,
  res: Response,
  next: NextFunction
) => {
  try {
    const status: number = error.status || 500;
    const message: string = error.message || "Unknown Error";

    // logger.error(`[${req.method}] ${req.path} >> StatusCode:: ${status}, Message:: ${message}`);
    res.status(status).json({ message });
  } catch (error) {
    next(error);
  }
};

export { validationMiddleware, HttpException, errorMiddleware };
