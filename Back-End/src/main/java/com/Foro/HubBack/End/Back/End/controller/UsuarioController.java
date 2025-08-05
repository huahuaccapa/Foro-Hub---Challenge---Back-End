package com.Foro.HubBack.End.Back.End.controller;

import com.Foro.HubBack.End.Back.End.dto.UsuarioDTO;
import com.Foro.HubBack.End.Back.End.model.Usuario;
import com.Foro.HubBack.End.Back.End.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Void> registrarUsuario(
            @RequestBody @Valid UsuarioDTO usuarioDTO,
            UriComponentsBuilder uriBuilder) {

        var usuario = new Usuario();
        usuario.setLogin(usuarioDTO.login());
        usuario.setClave(passwordEncoder.encode(usuarioDTO.clave()));
        usuario.setRol(usuarioDTO.rol() != null ? usuarioDTO.rol() : "USER");

        usuarioRepository.save(usuario);

        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}