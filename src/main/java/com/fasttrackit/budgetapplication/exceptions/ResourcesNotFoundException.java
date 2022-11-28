package com.fasttrackit.budgetapplication.exceptions;

public class ResourcesNotFoundException extends RuntimeException{
    private int entityId;

    public ResourcesNotFoundException(String message, int entityId) {
        super(message);
        this.entityId = entityId;
    }
}
