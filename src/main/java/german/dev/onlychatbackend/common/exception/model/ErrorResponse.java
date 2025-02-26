package german.dev.onlychatbackend.common.exception.model;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private int status;
    private String error;
    private String message;
    private String path;

    @Builder.Default
    private Map<String,String> details = new HashMap<>();

}
