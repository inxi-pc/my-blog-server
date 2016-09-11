# This my first blog


# Naming Rule:

1. Folder name must be singular, like `model`, `resource`

2. Class name must be singular, and came-case, the first letter must be capitals, like `PostResource`, `Post`


# System structure

1. Resource layer

function: Validate the request param & transfer transfer object to service

2. Service layer

function: Use different dao to finish the requirement, like get a post with its

all comment. In this method, we will use two dao to finish work

3. Dao layer

Type: Dao Factory

function: Manage DB's connection & do CURD


