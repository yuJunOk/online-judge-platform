/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ResponseEntityBoolean } from "../models/ResponseEntityBoolean";
import type { ResponseEntityInteger } from "../models/ResponseEntityInteger";
import type { ResponseEntityIPageUserVo } from "../models/ResponseEntityIPageUserVo";
import type { ResponseEntityLong } from "../models/ResponseEntityLong";
import type { ResponseEntityUserVo } from "../models/ResponseEntityUserVo";
import type { UserDo } from "../models/UserDo";
import type { UserLoginDto } from "../models/UserLoginDto";
import type { UserQueryPageDto } from "../models/UserQueryPageDto";
import type { UserRegisterDto } from "../models/UserRegisterDto";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class UserControllerService {
  /**
   * @param requestBody
   * @returns ResponseEntityBoolean OK
   * @throws ApiError
   */
  public static updateUser(
    requestBody: UserDo
  ): CancelablePromise<ResponseEntityBoolean> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/update",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns ResponseEntityLong OK
   * @throws ApiError
   */
  public static userRegister(
    requestBody: UserRegisterDto
  ): CancelablePromise<ResponseEntityLong> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/register",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns ResponseEntityIPageUserVo OK
   * @throws ApiError
   */
  public static getUserPage(
    requestBody: UserQueryPageDto
  ): CancelablePromise<ResponseEntityIPageUserVo> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/page",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @returns ResponseEntityInteger OK
   * @throws ApiError
   */
  public static userLogout(): CancelablePromise<ResponseEntityInteger> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/logout",
    });
  }

  /**
   * @param requestBody
   * @returns ResponseEntityUserVo OK
   * @throws ApiError
   */
  public static userLogin(
    requestBody: UserLoginDto
  ): CancelablePromise<ResponseEntityUserVo> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/login",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns ResponseEntityBoolean OK
   * @throws ApiError
   */
  public static deleteUser(
    requestBody: number
  ): CancelablePromise<ResponseEntityBoolean> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/delete",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns ResponseEntityBoolean OK
   * @throws ApiError
   */
  public static addUser(
    requestBody: UserDo
  ): CancelablePromise<ResponseEntityBoolean> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/add",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param email
   * @returns ResponseEntityBoolean OK
   * @throws ApiError
   */
  public static getMailCaptcha(
    email: string
  ): CancelablePromise<ResponseEntityBoolean> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/user/mailCaptcha",
      query: {
        email: email,
      },
    });
  }

  /**
   * @returns ResponseEntityUserVo OK
   * @throws ApiError
   */
  public static getCurrentUser(): CancelablePromise<ResponseEntityUserVo> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/user/current",
    });
  }
}
