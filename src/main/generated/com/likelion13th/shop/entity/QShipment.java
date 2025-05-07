package com.likelion13th.shop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShipment is a Querydsl query type for Shipment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShipment extends EntityPathBase<Shipment> {

    private static final long serialVersionUID = 2061215045L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShipment shipment = new QShipment("shipment");

    public final DateTimePath<java.time.LocalDateTime> deliveredAt = createDateTime("deliveredAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOrder order;

    public final DateTimePath<java.time.LocalDateTime> shippedAt = createDateTime("shippedAt", java.time.LocalDateTime.class);

    public final EnumPath<ShipmentStatus> status = createEnum("status", ShipmentStatus.class);

    public final StringPath trackingNum = createString("trackingNum");

    public QShipment(String variable) {
        this(Shipment.class, forVariable(variable), INITS);
    }

    public QShipment(Path<? extends Shipment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShipment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShipment(PathMetadata metadata, PathInits inits) {
        this(Shipment.class, metadata, inits);
    }

    public QShipment(Class<? extends Shipment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new QOrder(forProperty("order"), inits.get("order")) : null;
    }

}

