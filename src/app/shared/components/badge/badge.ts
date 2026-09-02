import { Component, Input } from '@angular/core';

type BadgeVariant =
  | 'default'
  | 'primary'
  | 'success'
  | 'warning'
  | 'danger';

type BadgeSize = 'sm' | 'md';

@Component({
  selector: 'app-badge',
  standalone: true,
  templateUrl: './badge.html',
  styleUrl: './badge.css'
})
export class BadgeComponent {

  @Input() variant: BadgeVariant = 'default';

  @Input() size: BadgeSize = 'md';

}