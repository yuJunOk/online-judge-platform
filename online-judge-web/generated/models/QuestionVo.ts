/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { JudgeConfigDto } from './JudgeConfigDto';
import type { UserVo } from './UserVo';
export type QuestionVo = {
    id?: number;
    title?: string;
    content?: string;
    tags?: Array<string>;
    submitNum?: number;
    acceptedNum?: number;
    judgeConfig?: JudgeConfigDto;
    thumbNum?: number;
    favourNum?: number;
    userId?: number;
    createTime?: string;
    updateTime?: string;
    userVo?: UserVo;
};

