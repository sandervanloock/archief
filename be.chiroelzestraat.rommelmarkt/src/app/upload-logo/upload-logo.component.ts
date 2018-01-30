import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FileValidators} from '../input-file/file-validators';

@Component( {
              selector: 'app-upload-logo', templateUrl: './upload-logo.component.html', styleUrls: ['./upload-logo.component.css']
            } )
export class UploadLogoComponent implements OnInit {
  @Input() s3: any;
  formDoc: FormGroup;

  constructor( private _fb: FormBuilder ) {
  }

  ngOnInit() {
    this.formDoc = this._fb.group( {
                                     requiredfile: [{value: undefined, disabled: false}, [Validators.required, FileValidators.maxContentSize( 104857600 )]],
                                   } );
  }

  onSubmit() {
    console.log( 'SUBMITTED', this.formDoc );
    const value = this.formDoc.controls.requiredfile.value;
    const file = value._files[0];
    const fileName = 'logos/'+file.name;

    this.s3.upload({
                Key: fileName,
                Body: file,
                ACL: 'public-read'
              }, function(err, data) {
      if (err) {
        return console.error('There was an error uploading your photo: ', err.message);
      }
      alert('Successfully uploaded photo.');
    });
  }

}
