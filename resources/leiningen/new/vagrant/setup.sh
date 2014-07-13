#!/bin/sh

curl -O https://github.com/twbs/bootstrap/archive/v3.2.0.zip
unzip x v3.2.0.zip 
mv v3.2.0/fonts public/fonts
mv v3.2.0/less public/less
rm -rf v3.2.0*
