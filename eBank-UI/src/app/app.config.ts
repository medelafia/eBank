import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideHttpClient } from '@angular/common/http';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeuix/themes/aura';
import { definePreset } from '@primeuix/themes';

const MyPreset = definePreset(Aura, {
    semantic: {
      primary: {
            50: '{fuchsia.50}',
            100: '{fuchsia.100}',
            200: '{fuchsia.200}',
            300: '{fuchsia.300}',
            400: '{fuchsia.400}',
            500: '{fuchsia.500}',
            600: '{fuchsia.600}',
            700: '{fuchsia.700}',
            800: '{fuchsia.800}',
            900: '{fuchsia.900}',
            950: '{fuchsia.950}'
      } , 

      surface: {
            50: '#ffffff',
            100: '#f9fafb',
            200: '#f3f4f6',
            300: '#e5e7eb',
            400: '#d1d5db',
            500: '#9ca3af',
            600: '#6b7280',
            700: '#4b5563',
            800: '#374151',
            900: '#1f2937'
        },
        colorScheme: {
            light: {
                semantic: {
                    highlight: {
                        background: '{primary.100}',
                        color: '{primary.700}',
                    }
                }
            },
            dark: {
                semantic: {
                    highlight: {
                        background: '{primary.800}',
                        color: '{primary.50}',
                    } 
                }
            }
        } , 
    }
});


export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes) , 
    provideHttpClient()  , 
    providePrimeNG({
      theme: {
          preset: MyPreset , 
          options : {
            darkModeSelector: '.app-dark', 
          }
      } 
    }), 
  ]
};
