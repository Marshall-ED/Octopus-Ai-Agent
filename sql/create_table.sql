CREATE TABLE `ai_chat_session` (
                                   `id` VARCHAR(36) NOT NULL COMMENT '自增主键ID',
                                   `session_id` VARCHAR(36) NOT NULL COMMENT '会话唯一标识(UUID)',
                                   `user_id` VARCHAR(64) NOT NULL COMMENT '用户标识',
                                   `title` VARCHAR(255) COMMENT '会话标题',
                                   `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `uniq_session_id` (`session_id`),
                                   INDEX `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI会话主表';

CREATE TABLE `ai_chat_message` (
                                   `id` VARCHAR(36) NOT NULL COMMENT '自增主键ID',
                                   `session_id` VARCHAR(36) NOT NULL COMMENT '关联的会话ID',
                                   `chat_ai_id` VARCHAR(36) NOT NULL COMMENT 'AI会话主表ID',
                                   `message_order` INT NOT NULL COMMENT '消息顺序（从0递增）',
                                   `role_type` VARCHAR(20) NOT NULL COMMENT '角色类型（USER/ASSISTANT/SYSTEM）',
                                   `content` TEXT NOT NULL COMMENT '消息内容',
                                   `tokens` INT DEFAULT 0 COMMENT '消息消耗的token数量',
                                   `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   PRIMARY KEY (`id`),
                                   INDEX `idx_session` (`session_id`),
                                   INDEX `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI消息记录表';
