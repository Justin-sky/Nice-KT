
//生成js
pbjs -t static-module -w commonjs -o pb.js login.proto benchmark.proto

 //生成d.ts
 pbts -o pb.d.ts pb.js