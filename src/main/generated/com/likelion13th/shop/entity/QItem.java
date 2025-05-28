package com.likelion13th.shop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = -1648477122L;

    public static final QItem item = new QItem("item");

    public final DateTimePath<java.time.LocalDateTime> createdBy = createDateTime("createdBy", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath itemDetail = createString("itemDetail");

    public final StringPath itemImg = createString("itemImg");

    public final StringPath itemImgPath = createString("itemImgPath");

    public final StringPath itemName = createString("itemName");

    public final EnumPath<ItemSellStatus> itemSellStatus = createEnum("itemSellStatus", ItemSellStatus.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedBy = createDateTime("modifiedBy", java.time.LocalDateTime.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public QItem(String variable) {
        super(Item.class, forVariable(variable));
    }

    public QItem(Path<? extends Item> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItem(PathMetadata metadata) {
        super(Item.class, metadata);
    }

}

