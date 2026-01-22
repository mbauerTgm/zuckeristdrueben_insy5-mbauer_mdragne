package com.mbauer_mdragne.vue_crud.Aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.mbauer_mdragne.vue_crud.Repositories.AnalysisRepository;
import com.mbauer_mdragne.vue_crud.Repositories.SampleRepository;
import com.mbauer_mdragne.vue_crud.Repositories.AnalysisSpecifications;
import com.mbauer_mdragne.vue_crud.Repositories.SampleSpecifications;

@Aspect
@Component
public class ResearcherSecurityAspect {

    @Pointcut("execution(* com.mbauer_mdragne.vue_crud.Repositories.AnalysisRepository.findAll(..))")
    public void analysisRepoFindAll() {}

    @Pointcut("execution(* com.mbauer_mdragne.vue_crud.Repositories.SampleRepository.findAll(..))")
    public void sampleRepoFindAll() {}

    @Around("analysisRepoFindAll() || sampleRepoFindAll()")
    public Object applyResearcherFilter(ProceedingJoinPoint joinPoint) throws Throwable {
        
        if (!isResearcher()) {
            return joinPoint.proceed();
        }

        Object[] args = joinPoint.getArgs();
        
        if (args.length > 0 && args[0] instanceof Specification) {
            
            Specification currentSpec = (Specification) args[0];
            Object targetRepo = joinPoint.getTarget(); 
            Specification restriction = null;

            if (targetRepo instanceof AnalysisRepository) {
                restriction = AnalysisSpecifications.researcherRestriction();
            } 
            else if (targetRepo instanceof SampleRepository) {
                restriction = SampleSpecifications.researcherRestriction();
            }

            if (restriction != null) {
                args[0] = currentSpec.and(restriction);
            }
        }
        
        return joinPoint.proceed(args);
    }

    private boolean isResearcher() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_Researcher"));
    }
}