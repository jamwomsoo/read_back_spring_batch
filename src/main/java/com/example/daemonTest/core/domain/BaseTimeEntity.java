package com.example.daemonTest.core.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * BaseTimeEntity
 * Describe :
 * 모든 Entity의 상위 클래스
 * CreatedDate, modifiedDate를 자동으로 관리하는 역할
 */
@Getter
@MappedSuperclass                                //Jpa Entity클래스들이 BaseTimeEntity을 상속할 경우 필드들(createdDate, modifiedDate)도 칼럼으로 인식하도록 합니다
@EntityListeners(AuditingEntityListener.class)   // BaseTimeEntity클래스에 Auditing 기능을 포함시킵니다.
public class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
