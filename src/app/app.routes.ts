import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'design-system',
    loadComponent: () =>
      import('./features/design-system/design-system')
        .then(m => m.DesignSystemComponent)
  }
];