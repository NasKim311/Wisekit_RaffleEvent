package com.wisekit.assignment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWinner is a Querydsl query type for Winner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWinner extends EntityPathBase<Winner> {

    private static final long serialVersionUID = 731820273L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWinner winner = new QWinner("winner");

    public final DatePath<java.time.LocalDate> ByLotDate = createDate("ByLotDate", java.time.LocalDate.class);

    public final QMember member;

    public final NumberPath<Long> winnerNum = createNumber("winnerNum", Long.class);

    public final NumberPath<Integer> winnerRank = createNumber("winnerRank", Integer.class);

    public QWinner(String variable) {
        this(Winner.class, forVariable(variable), INITS);
    }

    public QWinner(Path<? extends Winner> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWinner(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWinner(PathMetadata metadata, PathInits inits) {
        this(Winner.class, metadata, inits);
    }

    public QWinner(Class<? extends Winner> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

