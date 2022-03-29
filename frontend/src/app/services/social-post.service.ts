import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SocialPostService {

  baseUrl = '/demo/upload';

  constructor(private _http: HttpClient) { }

  postToSocial(link: string, message: string, platforms: any, imageAddress: string): Observable<any> 
  {
    return this._http.post(`${this.baseUrl}/?platforms=${platforms}&message=${message}&link=${link}&imageAddress=${imageAddress}`,null);
  }
}
