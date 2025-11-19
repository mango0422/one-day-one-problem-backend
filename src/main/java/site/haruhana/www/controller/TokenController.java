package site.haruhana.www.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.haruhana.www.dto.BaseResponse;
import site.haruhana.www.dto.user.TokenDto;
import site.haruhana.www.dto.user.TokenRefreshRequestDto;
import site.haruhana.www.utils.JwtUtil;

import static site.haruhana.www.common.ErrorCode.TOKEN_REFRESH_SUCCESS;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class TokenController {

    private final JwtUtil jwtUtil;

    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse<TokenDto>> refreshToken(@RequestBody TokenRefreshRequestDto requestDto) {
        TokenDto newTokens = jwtUtil.refreshTokens(requestDto.getRefreshToken());
        return TOKEN_REFRESH_SUCCESS.toResponseEntity(newTokens);
    }
}
