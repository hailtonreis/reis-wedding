import { Component } from '@angular/core';

import { ButtonComponent } from '../../shared/components/button/button';
import { InputComponent } from '../../shared/components/input/input';
import { TextareaComponent } from '../../shared/components/textarea/textarea';
import { CardComponent } from '../../shared/components/card/card';
import { BadgeComponent } from '../../shared/components/badge/badge';

@Component({
  selector: 'app-design-system',
  standalone: true,
  imports: [
    ButtonComponent,
    InputComponent,
    TextareaComponent,
    CardComponent,
    BadgeComponent
  ],
  templateUrl: './design-system.html',
  styleUrl: './design-system.css'
})
export class DesignSystemComponent {}