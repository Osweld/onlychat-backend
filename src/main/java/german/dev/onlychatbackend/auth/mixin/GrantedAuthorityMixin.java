package german.dev.onlychatbackend.auth.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class GrantedAuthorityMixin {
    @JsonCreator
    protected GrantedAuthorityMixin(@JsonProperty("authority") String authority) {
    }
}