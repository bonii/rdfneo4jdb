#!/bin/bash
diff -B <(sort $1 | uniq) <(sort $2 | uniq)
