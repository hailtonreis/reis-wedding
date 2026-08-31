import { Component, Input } from '@angular/core';

type TextareaSize = 'sm' | 'md' | 'lg';
type TextareaResize = 'none' | 'vertical' | 'both';

@Component({
  selector: 'app-textarea',
  standalone: true,
  templateUrl: './textarea.html',
  styleUrl: './textarea.css'
})
export class TextareaComponent {

  private static nextId = 0;

  @Input() id = `rw-textarea-${TextareaComponent.nextId++}`;

  @Input() label = '';

  @Input() placeholder = '';

  @Input() value = '';

  @Input() size: TextareaSize = 'md';

  @Input() rows = 4;

  @Input() resize: TextareaResize = 'vertical';

  @Input() disabled = false;

  @Input() required = false;

  @Input() error = '';

  @Input() helperText = '';

}