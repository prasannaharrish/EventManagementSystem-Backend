// package com.project.eventManagement.aop;

// import java.util.ArrayList;

// import org.aspectj.lang.annotation.AfterThrowing;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Pointcut;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Component;
// import org.springframework.web.bind.annotation.ExceptionHandler;

// import com.project.eventManagement.advice.ErrorResponse;
// import com.project.eventManagement.exception.UnAuthorizedAccessException;

// @Aspect
// @Component
// public class JwtExceptionHandlerAspect {

//     @Pointcut("execution(* com.project.eventManagement.config.CustomJwtAuthenticationConverter.convert(..))")
//     private void jwtExceptionPointCut() {

//     }

//     @AfterThrowing(pointcut = "jwtExceptionPointCut()", throwing = "unAuthorizedAccessException")


//     public ResponseEntity<ErrorResponse> handleJWTVAlidationException(
//             UnAuthorizedAccessException unAuthorizedAccessException) {
//         ArrayList<String> errorMessage = new ArrayList<>();
//         errorMessage.add(unAuthorizedAccessException.getMessage());
//         ErrorResponse errorResponse = new ErrorResponse(
//                 HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), errorMessage);
//         return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
//     }
// }
