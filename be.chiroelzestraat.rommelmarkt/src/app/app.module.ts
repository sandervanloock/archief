import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {ListLogosComponent} from './list-logos/list-logos.component';
import {MatCardModule, MatFormFieldModule, MatGridListModule, MatIconModule} from '@angular/material';
import {UploadLogoComponent} from './upload-logo/upload-logo.component';
import {InputFileComponent} from './input-file/input-file.component';
import {ReactiveFormsModule} from '@angular/forms';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {ByteFormatPipe} from './input-file/byte-format.pipe';

@NgModule( {
             declarations: [AppComponent, ListLogosComponent, UploadLogoComponent, InputFileComponent,ByteFormatPipe],
             imports: [BrowserModule, MatCardModule, MatGridListModule, MatFormFieldModule, MatIconModule, ReactiveFormsModule,NoopAnimationsModule],
             providers: [],
             bootstrap: [AppComponent]
           } )
export class AppModule {


}
