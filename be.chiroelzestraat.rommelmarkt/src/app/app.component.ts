import {Component, OnInit} from '@angular/core';

declare const AWS: any;

@Component( {
              selector: 'app-root', templateUrl: './app.component.html', styleUrls: ['./app.component.css']
            } )
export class AppComponent implements OnInit {

  public s3: any;

  ngOnInit(): void {
    const albumBucketName = 'rommelmarkt';
    const bucketRegion = 'eu-west-1';
    const IdentityPoolId = 'eu-west-1:99f0bb3c-c466-4354-9b77-0c4490470523';
    AWS.config.update( {
                         region: bucketRegion, credentials: new AWS.CognitoIdentityCredentials( {
                                                                                                  IdentityPoolId: IdentityPoolId
                                                                                                } )
                       } );

    this.s3 = new AWS.S3( {
                            apiVersion: '2006-03-01', params: {Bucket: albumBucketName}
                          } );
  }


}
