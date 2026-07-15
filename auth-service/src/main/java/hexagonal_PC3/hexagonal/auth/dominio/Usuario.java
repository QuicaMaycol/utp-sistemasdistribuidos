package hexagonal_PC3.hexagonal.auth.dominio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    private Long id;
    private String username;
    private String password;
    private String rol;
    private String mfaSecret;
    private boolean mfaEnabled;
}
