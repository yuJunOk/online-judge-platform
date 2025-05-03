/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { JudgeConfigDto } from './JudgeConfigDto';
import type { QuestionVo } from './QuestionVo';
import type { UserVo } from './UserVo';
export type QuestionSubmitVo = {
    id?: number;
    language?: string;
    code?: string;
    judgeInfo?: JudgeConfigDto;
    status?: number;
    questionId?: number;
    userId?: number;
    createTime?: string;
    updateTime?: string;
    userVo?: UserVo;
    questionVO?: QuestionVo;
};

