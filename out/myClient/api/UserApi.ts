/**
 * Auto-generated file, DO NOT MODIFY.
 */

/* tslint:disable */
/* eslint-disable */
/**
 * OpenAPI Petstore
 * This is a sample server Petstore server. For this sample, you can use the api key `special-key` to test the authorization filters.
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


import "reflect-metadata";
import { NextFunction, Request, Response, Router } from "express";
import { UserApiImplementation } from "../../api";
import { Type } from "class-transformer";
import { plainToInstance } from "class-transformer";
import { IsOptional, IsString, IsBoolean, IsEmail, IsNumber, IsDate } from "class-validator";
import { validationMiddleware } from "../runtime";
import type {
  Array,
  User,
} from '../models';

const router = Router();

export type CreateUserOperationResponse =
    {
      code: 0,
      response: any,
    };

export interface CreateUserResponse extends Response<
    any
> { };


class CreateUserRequestBodyParams {
    
    
    
    
    
    @IsOptional()
    id?: number;
    @IsString()
    
    
    
    
    
    username: String;
    @IsString()
    
    
    
    
    @IsOptional()
    firstName?: String;
    @IsString()
    
    
    
    
    @IsOptional()
    lastName?: String;
    @IsString()
    
    
    
    
    @IsOptional()
    email?: String;
    @IsString()
    
    
    
    
    @IsOptional()
    password?: String;
    @IsString()
    
    
    
    
    @IsOptional()
    phone?: String;
    
    
    
    
    
    @IsOptional()
    userStatus?: number;
}


export interface CreateUserRequest extends Request<
  // path params
  {},
  // response body reference, should match response
    any
  ,
  // body params
  CreateUserRequestBodyParams,
  // query params
  {}
> { };

export type CreateUsersWithArrayInputOperationResponse =
    {
      code: 0,
      response: any,
    };

export interface CreateUsersWithArrayInputResponse extends Response<
    any
> { };


class CreateUsersWithArrayInputRequestBodyParams {
}


export interface CreateUsersWithArrayInputRequest extends Request<
  // path params
  {},
  // response body reference, should match response
    any
  ,
  // body params
  CreateUsersWithArrayInputRequestBodyParams,
  // query params
  {}
> { };

export type CreateUsersWithListInputOperationResponse =
    {
      code: 0,
      response: any,
    };

export interface CreateUsersWithListInputResponse extends Response<
    any
> { };


class CreateUsersWithListInputRequestBodyParams {
}


export interface CreateUsersWithListInputRequest extends Request<
  // path params
  {},
  // response body reference, should match response
    any
  ,
  // body params
  CreateUsersWithListInputRequestBodyParams,
  // query params
  {}
> { };

export type DeleteUserOperationResponse =
    {
      code: 400,
      response: any,
    } |
    {
      code: 404,
      response: any,
    };

export interface DeleteUserResponse extends Response<
    any |
    any
> { };

class DeleteUserRequestPathParams {
    @IsString()
    
    
    
    
    
    username: String;
}



export interface DeleteUserRequest extends Request<
  // path params
  DeleteUserRequestPathParams,
  // response body reference, should match response
    any |
      any
  ,
  // body params
  {},
  // query params
  {}
> { };

export type GetUserByNameOperationResponse =
    {
      code: 200,
      response: User,
    } |
    {
      code: 400,
      response: any,
    } |
    {
      code: 404,
      response: any,
    };

export interface GetUserByNameResponse extends Response<
    User |
    any |
    any
> { };

class GetUserByNameRequestPathParams {
    @IsString()
    
    
    
    
    
    username: String;
}



export interface GetUserByNameRequest extends Request<
  // path params
  GetUserByNameRequestPathParams,
  // response body reference, should match response
    User |
      any |
      any
  ,
  // body params
  {},
  // query params
  {}
> { };

export type LoginUserOperationResponse =
    {
      code: 200,
      response: String,
    } |
    {
      code: 400,
      response: any,
    };

export interface LoginUserResponse extends Response<
    String |
    any
> { };



class LoginUserRequestQueryParams {
    @IsString()
    
    
    
    
    
    username: String;
    @IsString()
    
    
    
    
    
    password: String;
}

export interface LoginUserRequest extends Request<
  // path params
  {},
  // response body reference, should match response
    String |
      any
  ,
  // body params
  {},
  // query params
  LoginUserRequestQueryParams
> { };

export type LogoutUserOperationResponse =
    {
      code: 0,
      response: any,
    };

export interface LogoutUserResponse extends Response<
    any
> { };




export interface LogoutUserRequest extends Request<
  // path params
  {},
  // response body reference, should match response
    any
  ,
  // body params
  {},
  // query params
  {}
> { };

export type UpdateUserOperationResponse =
    {
      code: 400,
      response: any,
    } |
    {
      code: 404,
      response: any,
    };

export interface UpdateUserResponse extends Response<
    any |
    any
> { };

class UpdateUserRequestPathParams {
    @IsString()
    
    
    
    
    
    username: String;
}

class UpdateUserRequestBodyParams {
    
    
    
    
    
    @IsOptional()
    id?: number;
    @IsString()
    
    
    
    
    
    username: String;
    @IsString()
    
    
    
    
    @IsOptional()
    firstName?: String;
    @IsString()
    
    
    
    
    @IsOptional()
    lastName?: String;
    @IsString()
    
    
    
    
    @IsOptional()
    email?: String;
    @IsString()
    
    
    
    
    @IsOptional()
    password?: String;
    @IsString()
    
    
    
    
    @IsOptional()
    phone?: String;
    
    
    
    
    
    @IsOptional()
    userStatus?: number;
}


export interface UpdateUserRequest extends Request<
  // path params
  UpdateUserRequestPathParams,
  // response body reference, should match response
    any |
      any
  ,
  // body params
  UpdateUserRequestBodyParams,
  // query params
  {}
> { };



// Operations Begin

/**
  * This can only be done by the logged in user.
  * Create user
  */
router.post(
  `/user`,
  validationMiddleware({
    body: CreateUserRequestBodyParams,
    query: undefined,
    params: undefined
  }) as any,
  async (request: CreateUserRequest, response: CreateUserResponse, next: NextFunction) => {
    // set request to be typed correctly. eg if query string has a number parameter, coerce it to number
    request.body = plainToInstance(CreateUserRequestBodyParams, request.body);
    request.query = request.query;
    request.params = request.params;

    const operationResponse: CreateUserOperationResponse  = await UserApiImplementation.CreateUser(request);
    response.status(operationResponse.code).send(operationResponse.response);
  }
);


/**
  * 
  * Creates list of users with given input array
  */
router.post(
  `/user/createWithArray`,
  validationMiddleware({
    body: CreateUsersWithArrayInputRequestBodyParams,
    query: undefined,
    params: undefined
  }) as any,
  async (request: CreateUsersWithArrayInputRequest, response: CreateUsersWithArrayInputResponse, next: NextFunction) => {
    // set request to be typed correctly. eg if query string has a number parameter, coerce it to number
    request.body = plainToInstance(CreateUsersWithArrayInputRequestBodyParams, request.body);
    request.query = request.query;
    request.params = request.params;

    const operationResponse: CreateUsersWithArrayInputOperationResponse  = await UserApiImplementation.CreateUsersWithArrayInput(request);
    response.status(operationResponse.code).send(operationResponse.response);
  }
);


/**
  * 
  * Creates list of users with given input array
  */
router.post(
  `/user/createWithList`,
  validationMiddleware({
    body: CreateUsersWithListInputRequestBodyParams,
    query: undefined,
    params: undefined
  }) as any,
  async (request: CreateUsersWithListInputRequest, response: CreateUsersWithListInputResponse, next: NextFunction) => {
    // set request to be typed correctly. eg if query string has a number parameter, coerce it to number
    request.body = plainToInstance(CreateUsersWithListInputRequestBodyParams, request.body);
    request.query = request.query;
    request.params = request.params;

    const operationResponse: CreateUsersWithListInputOperationResponse  = await UserApiImplementation.CreateUsersWithListInput(request);
    response.status(operationResponse.code).send(operationResponse.response);
  }
);


/**
  * This can only be done by the logged in user.
  * Delete user
  */
router.delete(
  `/user/{username}`,
  validationMiddleware({
    body: undefined,
    query: undefined,
    params: DeleteUserRequestPathParams
  }) as any,
  async (request: DeleteUserRequest, response: DeleteUserResponse, next: NextFunction) => {
    // set request to be typed correctly. eg if query string has a number parameter, coerce it to number
    request.body = request.body;
    request.query = request.query;
    request.params = plainToInstance(DeleteUserRequestPathParams, request.params);

    const operationResponse: DeleteUserOperationResponse  = await UserApiImplementation.DeleteUser(request);
    response.status(operationResponse.code).send(operationResponse.response);
  }
);


/**
  * 
  * Get user by user name
  */
router.get(
  `/user/{username}`,
  validationMiddleware({
    body: undefined,
    query: undefined,
    params: GetUserByNameRequestPathParams
  }) as any,
  async (request: GetUserByNameRequest, response: GetUserByNameResponse, next: NextFunction) => {
    // set request to be typed correctly. eg if query string has a number parameter, coerce it to number
    request.body = request.body;
    request.query = request.query;
    request.params = plainToInstance(GetUserByNameRequestPathParams, request.params);

    const operationResponse: GetUserByNameOperationResponse  = await UserApiImplementation.GetUserByName(request);
    response.status(operationResponse.code).send(operationResponse.response);
  }
);


/**
  * 
  * Logs user into the system
  */
router.get(
  `/user/login`,
  validationMiddleware({
    body: undefined,
    query: LoginUserRequestQueryParams,
    params: undefined
  }) as any,
  async (request: LoginUserRequest, response: LoginUserResponse, next: NextFunction) => {
    // set request to be typed correctly. eg if query string has a number parameter, coerce it to number
    request.body = request.body;
    request.query = plainToInstance(LoginUserRequestQueryParams, request.query);
    request.params = request.params;

    const operationResponse: LoginUserOperationResponse  = await UserApiImplementation.LoginUser(request);
    response.status(operationResponse.code).send(operationResponse.response);
  }
);


/**
  * 
  * Logs out current logged in user session
  */
router.get(
  `/user/logout`,
  validationMiddleware({
    body: undefined,
    query: undefined,
    params: undefined
  }) as any,
  async (request: LogoutUserRequest, response: LogoutUserResponse, next: NextFunction) => {
    // set request to be typed correctly. eg if query string has a number parameter, coerce it to number
    request.body = request.body;
    request.query = request.query;
    request.params = request.params;

    const operationResponse: LogoutUserOperationResponse  = await UserApiImplementation.LogoutUser(request);
    response.status(operationResponse.code).send(operationResponse.response);
  }
);


/**
  * This can only be done by the logged in user.
  * Updated user
  */
router.put(
  `/user/{username}`,
  validationMiddleware({
    body: UpdateUserRequestBodyParams,
    query: undefined,
    params: UpdateUserRequestPathParams
  }) as any,
  async (request: UpdateUserRequest, response: UpdateUserResponse, next: NextFunction) => {
    // set request to be typed correctly. eg if query string has a number parameter, coerce it to number
    request.body = plainToInstance(UpdateUserRequestBodyParams, request.body);
    request.query = request.query;
    request.params = plainToInstance(UpdateUserRequestPathParams, request.params);

    const operationResponse: UpdateUserOperationResponse  = await UserApiImplementation.UpdateUser(request);
    response.status(operationResponse.code).send(operationResponse.response);
  }
);

// Operations End

export { router as UserApiRouter };

