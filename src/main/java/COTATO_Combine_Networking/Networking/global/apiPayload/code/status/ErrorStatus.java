package COTATO_Combine_Networking.Networking.global.apiPayload.code.status;



import org.springframework.http.HttpStatus;

import COTATO_Combine_Networking.Networking.global.apiPayload.code.BaseErrorCode;
import COTATO_Combine_Networking.Networking.global.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

	// For test
	TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "이거는 테스트"),

	// 가장 일반적인 응답
	_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
	_BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
	_UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
	_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

	// 로그인 관련 에러
	DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "4001", "이미 존재하는 사용자입니다."),
	USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "4002", "사용자를 찾을 수 없습니다."),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "4003", "비밀번호가 일치하지 않습니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "4004", "로그인이 필요합니다."),

	// 장소 관련 에러
	PLACE_ALREADY_EXISTS(HttpStatus.CONFLICT, "2001", "이미 등록된 장소입니다."),
	PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "2002", "존재하지 않는 장소입니다."),
	ALREADY_PINNED(HttpStatus.BAD_REQUEST, "2003", "이미 즐겨찾기된 장소입니다."),
	ALREADY_UNPINNED(HttpStatus.BAD_REQUEST, "2004", "이미 즐겨찾기가 해제된 장소입니다.");


	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ErrorReasonDTO getReason() {
		return ErrorReasonDTO.builder()
			.message(message)
			.code(code)
			.isSuccess(false)
			.build();
	}


	@Override
	public ErrorReasonDTO getReasonHttpStatus() {
		return ErrorReasonDTO.builder()
			.message(message)
			.code(code)
			.isSuccess(false)
			.httpStatus(httpStatus)
			.build();
	}
}
