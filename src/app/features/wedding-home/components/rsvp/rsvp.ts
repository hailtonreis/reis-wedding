import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ConviteService } from './convite';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-rsvp',
  imports: [FormsModule],
  templateUrl: './rsvp.html',
  styleUrl: './rsvp.css',
})
export class RsvpComponent implements OnInit {

  private route = inject(ActivatedRoute);
  private conviteService = inject(ConviteService);

  token: string | null = null;

  nomeConvidado = '';
  limiteAcompanhantes = 0;
  statusConvite = '';

  // Lista de acompanhantes
  acompanhantes: string[] = [];

  ngOnInit(): void {
    this.route.queryParamMap.subscribe(params => {
      this.token = params.get('token');

      if (this.token) {
        this.buscarConvite(this.token);
      } else {
        console.log('Nenhum token informado na URL.');
      }
    });
  }

  private buscarConvite(token: string): void {
    this.conviteService.buscarPorToken(token).subscribe({
      next: (convite) => {
        console.log('Convite encontrado:', convite);

        this.nomeConvidado = convite.nomeConvidado;
        this.limiteAcompanhantes = convite.limiteAcompanhantes;
        this.statusConvite = convite.status;
      },
      error: (erro) => {
        console.error('Erro ao buscar convite:', erro);
      }
    });
  }

  // Adiciona um acompanhante respeitando o limite
  adicionarAcompanhante(): void {
    if (this.acompanhantes.length < this.limiteAcompanhantes) {
      this.acompanhantes.push('');
    }
  }

  // Remove um acompanhante da lista
  removerAcompanhante(index: number): void {
    this.acompanhantes.splice(index, 1);
  }

}