
import { AuthService } from '@/services/auth-services/auth-service';
import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const authService : AuthService = inject(AuthService) 
  const router : Router = inject(Router)
  if(authService.isAuthenticated()) { 
    return true ; 
  }
  return router.createUrlTree(['/login'], {
    queryParams: { returnUrl: state.url }
  });
};
