
CREATE TABLE nop_batch_file(
  SID VARCHAR2(32) NOT NULL ,
  FILE_NAME VARCHAR2(500) NOT NULL ,
  FILE_PATH VARCHAR2(2000) NOT NULL ,
  FILE_LENGTH NUMBER(20) NOT NULL ,
  FILE_CATEGORY VARCHAR2(100) NOT NULL ,
  FILE_SOURCE VARCHAR2(10) NOT NULL ,
  BATCH_TASK_ID VARCHAR2(32) NOT NULL ,
  PROCESS_STATE VARCHAR2(10) NOT NULL ,
  ACCEPT_DATE DATE NOT NULL ,
  VERSION NUMBER(20) NOT NULL ,
  CREATED_BY VARCHAR2(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  UPDATED_BY VARCHAR2(50) NOT NULL ,
  UPDATE_TIME TIMESTAMP NOT NULL ,
  REMARK VARCHAR2(200)  ,
  constraint PK_nop_batch_file primary key (SID)
);

CREATE TABLE nop_batch_task(
  SID VARCHAR2(32) NOT NULL ,
  TASK_NAME VARCHAR2(50) NOT NULL ,
  TASK_KEY VARCHAR2(100) NOT NULL ,
  TASK_STATUS INTEGER NOT NULL ,
  START_TIME TIMESTAMP  ,
  END_TIME TIMESTAMP  ,
  TASK_PARAMS VARCHAR2(4000)  ,
  EXEC_COUNT INTEGER NOT NULL ,
  WORKER_ID VARCHAR2(100) NOT NULL ,
  INPUT_FILE_ID VARCHAR2(32)  ,
  FLOW_STEP_ID VARCHAR2(50)  ,
  FLOW_ID VARCHAR2(50)  ,
  RESTART_TIME TIMESTAMP  ,
  RESULT_STATUS INTEGER  ,
  RESULT_CODE VARCHAR2(100)  ,
  RESULT_MSG VARCHAR2(500)  ,
  ERROR_STACK VARCHAR2(4000)  ,
  COMPLETED_INDEX NUMBER(20) NOT NULL ,
  COMPLETE_ITEM_COUNT NUMBER(20) NOT NULL ,
  LOAD_RETRY_COUNT INTEGER NOT NULL ,
  LOAD_SKIP_COUNT NUMBER(20) NOT NULL ,
  RETRY_ITEM_COUNT INTEGER NOT NULL ,
  PROCESS_ITEM_COUNT NUMBER(20) NOT NULL ,
  SKIP_ITEM_COUNT NUMBER(20) NOT NULL ,
  WRITE_ITEM_COUNT NUMBER(20) NOT NULL ,
  VERSION NUMBER(20) NOT NULL ,
  CREATED_BY VARCHAR2(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  UPDATED_BY VARCHAR2(50) NOT NULL ,
  UPDATE_TIME TIMESTAMP NOT NULL ,
  REMARK VARCHAR2(200)  ,
  constraint PK_nop_batch_task primary key (SID)
);

CREATE TABLE nop_batch_task_var(
  BATCH_TASK_ID VARCHAR2(32) NOT NULL ,
  FIELD_NAME VARCHAR2(100) NOT NULL ,
  FIELD_TYPE INTEGER NOT NULL ,
  STRING_VALUE VARCHAR2(4000)  ,
  DECIMAL_VALUE NUMBER(30,6)  ,
  LONG_VALUE NUMBER(20)  ,
  DATE_VALUE DATE  ,
  TIMESTAMP_VALUE TIMESTAMP  ,
  VERSION NUMBER(20) NOT NULL ,
  CREATED_BY VARCHAR2(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  UPDATED_BY VARCHAR2(50) NOT NULL ,
  UPDATE_TIME TIMESTAMP NOT NULL ,
  constraint PK_nop_batch_task_var primary key (BATCH_TASK_ID,FIELD_NAME)
);

CREATE TABLE nop_batch_record_result(
  BATCH_TASK_ID VARCHAR2(32) NOT NULL ,
  RECORD_KEY VARCHAR2(200) NOT NULL ,
  RESULT_STATUS INTEGER NOT NULL ,
  RESULT_CODE VARCHAR2(100)  ,
  RESULT_MSG VARCHAR2(500)  ,
  ERROR_STACK VARCHAR2(4000)  ,
  RECORD_INFO VARCHAR2(2000)  ,
  RETRY_COUNT INTEGER NOT NULL ,
  BATCH_SIZE INTEGER NOT NULL ,
  HANDLE_STATUS INTEGER NOT NULL ,
  VERSION NUMBER(20) NOT NULL ,
  CREATED_BY VARCHAR2(50) NOT NULL ,
  CREATE_TIME TIMESTAMP NOT NULL ,
  UPDATED_BY VARCHAR2(50) NOT NULL ,
  UPDATE_TIME TIMESTAMP NOT NULL ,
  constraint PK_nop_batch_record_result primary key (BATCH_TASK_ID,RECORD_KEY)
);


      COMMENT ON TABLE nop_batch_file IS '批处理文件';
                
      COMMENT ON COLUMN nop_batch_file.SID IS '主键';
                    
      COMMENT ON COLUMN nop_batch_file.FILE_NAME IS '文件名';
                    
      COMMENT ON COLUMN nop_batch_file.FILE_PATH IS '文件路径';
                    
      COMMENT ON COLUMN nop_batch_file.FILE_LENGTH IS '文件长度';
                    
      COMMENT ON COLUMN nop_batch_file.FILE_CATEGORY IS '文件分类';
                    
      COMMENT ON COLUMN nop_batch_file.FILE_SOURCE IS '文件来源';
                    
      COMMENT ON COLUMN nop_batch_file.BATCH_TASK_ID IS '批处理任务';
                    
      COMMENT ON COLUMN nop_batch_file.PROCESS_STATE IS '处理状态';
                    
      COMMENT ON COLUMN nop_batch_file.ACCEPT_DATE IS '文件接收时间';
                    
      COMMENT ON COLUMN nop_batch_file.VERSION IS '数据版本';
                    
      COMMENT ON COLUMN nop_batch_file.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_batch_file.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_batch_file.UPDATED_BY IS '修改人';
                    
      COMMENT ON COLUMN nop_batch_file.UPDATE_TIME IS '修改时间';
                    
      COMMENT ON COLUMN nop_batch_file.REMARK IS '备注';
                    
      COMMENT ON TABLE nop_batch_task IS '批处理任务';
                
      COMMENT ON COLUMN nop_batch_task.SID IS 'SID';
                    
      COMMENT ON COLUMN nop_batch_task.TASK_NAME IS '任务名';
                    
      COMMENT ON COLUMN nop_batch_task.TASK_KEY IS '唯一Key';
                    
      COMMENT ON COLUMN nop_batch_task.TASK_STATUS IS '任务状态';
                    
      COMMENT ON COLUMN nop_batch_task.START_TIME IS '任务启动时间';
                    
      COMMENT ON COLUMN nop_batch_task.END_TIME IS '任务结束时间';
                    
      COMMENT ON COLUMN nop_batch_task.TASK_PARAMS IS '任务参数';
                    
      COMMENT ON COLUMN nop_batch_task.EXEC_COUNT IS '执行次数';
                    
      COMMENT ON COLUMN nop_batch_task.WORKER_ID IS '执行者';
                    
      COMMENT ON COLUMN nop_batch_task.INPUT_FILE_ID IS '输入文件';
                    
      COMMENT ON COLUMN nop_batch_task.FLOW_STEP_ID IS '关联流程步骤ID';
                    
      COMMENT ON COLUMN nop_batch_task.FLOW_ID IS '关联流程ID';
                    
      COMMENT ON COLUMN nop_batch_task.RESTART_TIME IS '重启时间';
                    
      COMMENT ON COLUMN nop_batch_task.RESULT_STATUS IS '返回状态码';
                    
      COMMENT ON COLUMN nop_batch_task.RESULT_CODE IS '返回码';
                    
      COMMENT ON COLUMN nop_batch_task.RESULT_MSG IS '返回消息';
                    
      COMMENT ON COLUMN nop_batch_task.ERROR_STACK IS '错误堆栈';
                    
      COMMENT ON COLUMN nop_batch_task.COMPLETED_INDEX IS '已完成记录下标';
                    
      COMMENT ON COLUMN nop_batch_task.COMPLETE_ITEM_COUNT IS '完成条目数量';
                    
      COMMENT ON COLUMN nop_batch_task.LOAD_RETRY_COUNT IS '重试加载次数';
                    
      COMMENT ON COLUMN nop_batch_task.LOAD_SKIP_COUNT IS '加载跳过数量';
                    
      COMMENT ON COLUMN nop_batch_task.RETRY_ITEM_COUNT IS '重试条目次数';
                    
      COMMENT ON COLUMN nop_batch_task.PROCESS_ITEM_COUNT IS '处理条目数量';
                    
      COMMENT ON COLUMN nop_batch_task.SKIP_ITEM_COUNT IS '跳过条目数量';
                    
      COMMENT ON COLUMN nop_batch_task.WRITE_ITEM_COUNT IS '写入条目数量';
                    
      COMMENT ON COLUMN nop_batch_task.VERSION IS '数据版本';
                    
      COMMENT ON COLUMN nop_batch_task.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_batch_task.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_batch_task.UPDATED_BY IS '修改人';
                    
      COMMENT ON COLUMN nop_batch_task.UPDATE_TIME IS '修改时间';
                    
      COMMENT ON COLUMN nop_batch_task.REMARK IS '备注';
                    
      COMMENT ON TABLE nop_batch_task_var IS '批处理任务状态变量';
                
      COMMENT ON COLUMN nop_batch_task_var.BATCH_TASK_ID IS '主键';
                    
      COMMENT ON COLUMN nop_batch_task_var.FIELD_NAME IS '变量名';
                    
      COMMENT ON COLUMN nop_batch_task_var.FIELD_TYPE IS '变量类型';
                    
      COMMENT ON COLUMN nop_batch_task_var.STRING_VALUE IS '字符串值';
                    
      COMMENT ON COLUMN nop_batch_task_var.DECIMAL_VALUE IS '浮点值';
                    
      COMMENT ON COLUMN nop_batch_task_var.LONG_VALUE IS '整数型';
                    
      COMMENT ON COLUMN nop_batch_task_var.DATE_VALUE IS '日期值';
                    
      COMMENT ON COLUMN nop_batch_task_var.TIMESTAMP_VALUE IS '时间点值';
                    
      COMMENT ON COLUMN nop_batch_task_var.VERSION IS '数据版本';
                    
      COMMENT ON COLUMN nop_batch_task_var.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_batch_task_var.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_batch_task_var.UPDATED_BY IS '修改人';
                    
      COMMENT ON COLUMN nop_batch_task_var.UPDATE_TIME IS '修改时间';
                    
      COMMENT ON TABLE nop_batch_record_result IS '批处理记录结果';
                
      COMMENT ON COLUMN nop_batch_record_result.BATCH_TASK_ID IS '主键';
                    
      COMMENT ON COLUMN nop_batch_record_result.RECORD_KEY IS '记录唯一键';
                    
      COMMENT ON COLUMN nop_batch_record_result.RESULT_STATUS IS '返回状态码';
                    
      COMMENT ON COLUMN nop_batch_record_result.RESULT_CODE IS '返回码';
                    
      COMMENT ON COLUMN nop_batch_record_result.RESULT_MSG IS '返回消息';
                    
      COMMENT ON COLUMN nop_batch_record_result.ERROR_STACK IS '错误堆栈';
                    
      COMMENT ON COLUMN nop_batch_record_result.RECORD_INFO IS '记录信息';
                    
      COMMENT ON COLUMN nop_batch_record_result.RETRY_COUNT IS '重试次数';
                    
      COMMENT ON COLUMN nop_batch_record_result.BATCH_SIZE IS '批次大小';
                    
      COMMENT ON COLUMN nop_batch_record_result.HANDLE_STATUS IS '处理状态';
                    
      COMMENT ON COLUMN nop_batch_record_result.VERSION IS '数据版本';
                    
      COMMENT ON COLUMN nop_batch_record_result.CREATED_BY IS '创建人';
                    
      COMMENT ON COLUMN nop_batch_record_result.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_batch_record_result.UPDATED_BY IS '修改人';
                    
      COMMENT ON COLUMN nop_batch_record_result.UPDATE_TIME IS '修改时间';
                    
