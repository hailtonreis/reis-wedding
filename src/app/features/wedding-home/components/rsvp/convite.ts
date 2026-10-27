import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ConviteResponse {
  id: number;
  casamentoId: number;
  nomeConvidado: string;
  telefone: string;
  token: string;
  status: string;
  limiteAcompanhantes: number;
  dataConfirmacao: string | null;
  createdAt: string;
  updatedAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class ConviteService {

  private http = inject(HttpClient);

  private readonly apiUrl = 'http://localhost:8080/convites';

  buscarPorToken(token: string): Observable<ConviteResponse> {
    return this.http.get<ConviteResponse>(
      `${this.apiUrl}/token/${encodeURIComponent(token)}`
    );
  }
}