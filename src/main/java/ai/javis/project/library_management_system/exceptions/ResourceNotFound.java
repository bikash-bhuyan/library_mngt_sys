package ai.javis.project.library_management_system.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ResourceNotFound extends RuntimeException{
    private String resourceName;
    private String fieldName;
    private long fieldValue;
    public ResourceNotFound(String resourceName, String fieldName, long fieldValue){
        super(String.format("%s with %s : %d not found", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}