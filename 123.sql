create table music.comments (
                                id bigint auto_increment comment '评论ID'
        primary key,
                                user_id int not null comment '用户ID',
                                content_type enum('song', 'album', 'artist', 'playlist') not null comment '评论对象类型',
                                content_id bigint not null comment '评论对象ID',
                                content text not null comment '评论内容',
                                created_at timestamp default current_timestamp comment '评论时间',
                                constraint comments_ibfk_1
                                    foreign key (user_id) references music.users (id)
                                        on delete cascade
);

create index comments_content_index
    on music.comments (content_type, content_id);

create index comments_user_id_index
    on music.comments (user_id);


CREATE TABLE music.comment_likes (
                                     id BIGINT AUTO_INCREMENT COMMENT '点赞ID' PRIMARY KEY,
                                     comment_id BIGINT NOT NULL COMMENT '评论ID',
                                     user_id INT NOT NULL COMMENT '用户ID',
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
                                     CONSTRAINT comment_likes_ibfk_1 FOREIGN KEY (comment_id) REFERENCES music.comments (id) ON DELETE CASCADE,
                                     CONSTRAINT comment_likes_ibfk_2 FOREIGN KEY (user_id) REFERENCES music.users (id) ON DELETE CASCADE,
                                     UNIQUE KEY unique_comment_user (comment_id, user_id) COMMENT '确保用户对同一评论只能点赞一次'
);

CREATE INDEX comment_likes_comment_id_idx ON music.comment_likes (comment_id);
CREATE INDEX comment_likes_user_id_idx ON music.comment_likes (user_id);