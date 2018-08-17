import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  constructor(private _http: HttpClient) { }

  pushFileToStorage(file: File, name: string): Observable<HttpEvent<{}>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    formData.append('name', name);

    const req = new HttpRequest('POST', 'http://localhost:8080/api/v1/upload', formData, {
        reportProgress: true,
        responseType: 'text'
    });

    return this._http.request(req);
  }

  getFiles(): Observable<any> {
    return this._http.get('http://localhost:8080/api/v1/files');
  }

}
