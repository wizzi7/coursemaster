import { Injectable } from '@angular/core';

@Injectable()
export class CryptoService {
  private iv = new TextEncoder().encode('4f57f68d0f3b4a2e9f1d2c5b');
  private pass = new TextEncoder().encode('4f57f68d0f3b4a2e9f1d2c5b').toString();

  async encrypt(text: string): Promise<string> {
    return await this._encrypt(text);
  }
  async _encrypt(data: string): Promise<string> {
    const algo = {
      name: 'AES-GCM',
      iv: this.iv,
      length: 256
    };

    const pwUtf8 = new TextEncoder().encode(this.pass);
    const pwHash = await crypto.subtle.digest('SHA-256', pwUtf8);

    const key = await crypto.subtle.importKey('raw', pwHash, algo, false, ['encrypt']);

    const ptUtf8 = new TextEncoder().encode(data);
    const ctBuffer = await crypto.subtle.encrypt(algo, key, ptUtf8);

    const ctArray = Array.from(new Uint8Array(ctBuffer));
    const ctStr = ctArray.map(byte => String.fromCharCode(byte)).join('');
    return btoa(ctStr);
  }

  async decrypt(encryptedText: string): Promise<string> {
    return await this._decrypt(encryptedText);
  }

  async _decrypt(data: string): Promise<string> {
    const algo = {
      name: 'AES-GCM',
      iv: this.iv,
      length: 256
    };

    const pwUtf8 = new TextEncoder().encode(this.pass);
    const pwHash = await crypto.subtle.digest('SHA-256', pwUtf8);

    const key = await crypto.subtle.importKey('raw', pwHash, algo, false, ['decrypt']);

    const ctStr = atob(data);
    const ctArray = Array.from(ctStr).map(ch => ch.charCodeAt(0));
    const ctUint8 = new Uint8Array(ctArray);

    const plainBuffer = await crypto.subtle.decrypt(algo, key, ctUint8);

    return new TextDecoder().decode(plainBuffer);
  }
}
