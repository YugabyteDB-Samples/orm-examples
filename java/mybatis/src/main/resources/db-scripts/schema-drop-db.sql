
    alter table orderline 
       drop constraint FK44tgjt0pognvcg1r7isplhg57;

    alter table orderline 
       drop constraint FK7jhn7tvm2wi722qnm2ilw06hh;

    alter table orders 
       drop constraint FK32ql8ubntj5uh44ph9659tiih;

    drop table if exists orderline cascade;

    drop table if exists orders cascade;

    drop table if exists products cascade;

    drop table if exists users cascade;
