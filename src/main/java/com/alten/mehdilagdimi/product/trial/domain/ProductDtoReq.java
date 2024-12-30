package com.alten.mehdilagdimi.product.trial.domain;

public record ProductDtoReq(
                      String code,
                      String name,
                      String description,
                      String image,
                      String category,
                      float price,
                      int quantity,
                      String internalReference,
                      int shellId,
                      InventoryStatus inventoryStatus,
                      short rating) {}
