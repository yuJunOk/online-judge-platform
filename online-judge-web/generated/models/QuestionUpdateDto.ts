/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { JudgeCaseDto } from './JudgeCaseDto';
import type { JudgeConfigDto } from './JudgeConfigDto';
export type QuestionUpdateDto = {
    id?: number;
    title?: string;
    content?: string;
    tags?: Array<string>;
    answer?: string;
    judgeCase?: Array<JudgeCaseDto>;
    judgeConfig?: JudgeConfigDto;
};

