import { Component, Input } from '@angular/core';

type InputSize = 'sm' | 'md' | 'lg';

@Component({
  selector: 'app-input',
  standalone: true,
  templateUrl: './input.html',
  styleUrl: './input.css'
})
export class InputComponent {

  @Input() id = '';

  @Input() label = '';

  @Input() placeholder = '';

  @Input() type = 'text';

  @Input() value = '';

  @Input() size: InputSize = 'md';

  @Input() disabled = false;

  @Input() required = false;

  @Input() error = '';

  @Input() helperText = '';

}