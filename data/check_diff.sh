#!/bin/bash
diff -B -w -a <(sort $1 | uniq) <(sort $2 | uniq)
