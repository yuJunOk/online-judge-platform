/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { IdDto } from "../models/IdDto";
import type { QuestionAddDto } from "../models/QuestionAddDto";
import type { QuestionEditDto } from "../models/QuestionEditDto";
import type { QuestionQueryPageDto } from "../models/QuestionQueryPageDto";
import type { QuestionSubmitAddDto } from "../models/QuestionSubmitAddDto";
import type { QuestionSubmitQueryPageDto } from "../models/QuestionSubmitQueryPageDto";
import type { QuestionUpdateDto } from "../models/QuestionUpdateDto";
import type { ResponseEntityBoolean } from "../models/ResponseEntityBoolean";
import type { ResponseEntityLong } from "../models/ResponseEntityLong";
import type { ResponseEntityPageQuestionDo } from "../models/ResponseEntityPageQuestionDo";
import type { ResponseEntityPageQuestionSubmitVo } from "../models/ResponseEntityPageQuestionSubmitVo";
import type { ResponseEntityPageQuestionVo } from "../models/ResponseEntityPageQuestionVo";
import type { ResponseEntityQuestionDo } from "../models/ResponseEntityQuestionDo";
import type { ResponseEntityQuestionVo } from "../models/ResponseEntityQuestionVo";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class QuestionControllerService {
  /**
   * @param requestBody
   * @returns ResponseEntityBoolean OK
   * @throws ApiError
   */
  public static updateQuestion(
    requestBody: QuestionUpdateDto
  ): CancelablePromise<ResponseEntityBoolean> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/question/update",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns ResponseEntityPageQuestionSubmitVo OK
   * @throws ApiError
   */
  public static listQuestionSubmitByPage(
    requestBody: QuestionSubmitQueryPageDto
  ): CancelablePromise<ResponseEntityPageQuestionSubmitVo> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/question/question_submit/list/page",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns ResponseEntityLong OK
   * @throws ApiError
   */
  public static doQuestionSubmit(
    requestBody: QuestionSubmitAddDto
  ): CancelablePromise<ResponseEntityLong> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/question/question_submit/do",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns ResponseEntityPageQuestionVo OK
   * @throws ApiError
   */
  public static listMyQuestionVoByPage(
    requestBody: QuestionQueryPageDto
  ): CancelablePromise<ResponseEntityPageQuestionVo> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/question/my/list/page/vo",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns ResponseEntityPageQuestionDo OK
   * @throws ApiError
   */
  public static listQuestionByPage(
    requestBody: QuestionQueryPageDto
  ): CancelablePromise<ResponseEntityPageQuestionDo> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/question/list/page",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns ResponseEntityPageQuestionVo OK
   * @throws ApiError
   */
  public static listQuestionVoByPage(
    requestBody: QuestionQueryPageDto
  ): CancelablePromise<ResponseEntityPageQuestionVo> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/question/list/page/vo",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns ResponseEntityBoolean OK
   * @throws ApiError
   */
  public static editQuestion(
    requestBody: QuestionEditDto
  ): CancelablePromise<ResponseEntityBoolean> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/question/edit",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns ResponseEntityBoolean OK
   * @throws ApiError
   */
  public static deleteQuestion(
    requestBody: IdDto
  ): CancelablePromise<ResponseEntityBoolean> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/question/delete",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns ResponseEntityLong OK
   * @throws ApiError
   */
  public static addQuestion(
    requestBody: QuestionAddDto
  ): CancelablePromise<ResponseEntityLong> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/question/add",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param id
   * @returns ResponseEntityQuestionDo OK
   * @throws ApiError
   */
  public static getQuestionById(
    id: number
  ): CancelablePromise<ResponseEntityQuestionDo> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/question/get",
      query: {
        id: id,
      },
    });
  }

  /**
   * @param id
   * @returns ResponseEntityQuestionVo OK
   * @throws ApiError
   */
  public static getQuestionVoById(
    id: number
  ): CancelablePromise<ResponseEntityQuestionVo> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/question/get/vo",
      query: {
        id: id,
      },
    });
  }
}
