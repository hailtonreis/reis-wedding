import { Component } from '@angular/core';
import { ButtonComponent } from '../../shared/components/button/button';
import { InputComponent } from '../../shared/components/input/input';

@Component({
  selector: 'app-design-system',
  standalone: true,
  imports: [ 
    ButtonComponent,
    InputComponent
 ],
  templateUrl: './design-system.html',
  styleUrl: './design-system.css',
})
export class DesignSystemComponent {}
