package br.leetjourney.semijoiasapi.api.controller;


import br.leetjourney.semijoiasapi.api.dto.request.LoginRequestDTO;
import br.leetjourney.semijoiasapi.api.dto.response.LoginResponseDTO;
import br.leetjourney.semijoiasapi.core.repository.UserRepository;
import br.leetjourney.semijoiasapi.core.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var user = (br.leetjourney.semijoiasapi.core.entity.User) auth.getPrincipal();
        var token = jwtService.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDTO(token, user.getName(), user.getRole().name()));
    }

}
