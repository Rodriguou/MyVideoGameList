package br.com.rodrigomatos.myvideogamelist.api.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Game with ID " + id + " not found");
    }
}
