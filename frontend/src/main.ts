import { provideHttpClient } from '@angular/common/http'; // Import this helper
import { bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config';

bootstrapApplication(AppComponent, {
  ...appConfig,
  providers: [
    provideHttpClient(),
    provideAnimations(), // Provide animations for ngx-toastr
    ...appConfig.providers,
  ],
}).catch((err) => console.error(err));
