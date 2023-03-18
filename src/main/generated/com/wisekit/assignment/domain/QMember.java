package com.wisekit.assignment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 441793356L;

    public static final QMember member = new QMember("member1");

    public final StringPath memberName = createString("memberName");

    public final NumberPath<Long> memberNum = createNumber("memberNum", Long.class);

    public final StringPath memberPhoneNum = createString("memberPhoneNum");

    public final ListPath<Winner, QWinner> winners = this.<Winner, QWinner>createList("winners", Winner.class, QWinner.class, PathInits.DIRECT2);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

