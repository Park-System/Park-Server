package parkSystem.park.jwt;

import lombok.Builder;

// JWT DTO
@Builder
public record JwtToken(String accessToken, String refreshToken) {}