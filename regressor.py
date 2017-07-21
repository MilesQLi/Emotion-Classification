# -*- coding: utf-8 -*-
import logging
import sys
from sklearn.linear_model import SGDClassifier
from sklearn.feature_extraction import DictVectorizer
from sklearn.ensemble import AdaBoostClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn import tree
from numpy import asarray
from random import random
from sklearn import svm
from sklearn.linear_model import BayesianRidge
from sklearn.linear_model import SGDRegressor
from numpy.random import shuffle
from sklearn.metrics import mean_squared_error
import numpy as np
from sklearn.grid_search import GridSearchCV
from sklearn.naive_bayes import GaussianNB
from numpy import array
from sklearn import tree
import cPickle
import joblib

first_run = False
method1 = True
method2 = True
method3 = True


# Support Vector Regression
if __name__ == '__main__':
    logging.basicConfig(level = logging.DEBUG,
                format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',
                datefmt='%a, %d %b %Y %H:%M:%S',
                filename='sa.log',
                filemode='w')

    logging.info('start to load data...')
    reader = open('weibo_train_xy.txt', 'r')
    data = []
    for instance in reader:
        instance = instance.split()
        y = int(instance[1])
        y1 = int(instance[2])
        y2 = int(instance[3])
        x = {'BIAS':1}
        for feature in instance[4:]:
            pos = feature.rfind(':')
            key, val = feature[:pos], feature[pos + 1:]
            if x.has_key(key):
                x[key] += float(val)
            else:
                x[key] = float(val)
        data.append([x, y, y1, y2])
    logging.info('Total number of instance: ' + str(len(data)))

#   random shuffle and split data into training set & test set
   # shuffle(data)
    X = [ins[0] for ins in data]
    Y = [ins[1] for ins in data]
    Y1 = [ins[2] for ins in data]
    Y2 = [ins[3] for ins in data]
    
    
    
    
    
  ##  mid = int(len(data) * .8)
    vectorizer = DictVectorizer()
    print 'here-2'
    X = vectorizer.fit_transform(X)
    print 'here-1'
  #  logging.info('Feature dimension: ' + str(len(X[0])))

  #  train_x, test_x = X[:mid], X[mid:]
  #  train_y, test_y = Y[:mid], Y[mid:]
     
    train_x, train_y = X, Y
     
    #logging.info('Number of train data: ' + str(len(train_x)))

    logging.info('start to train model...')
    # linear kernel seems to take a much longer time to train
    
    svm_param_grid = [
        {'C': [1, 10, 100, 120, 150], 'kernel': ['linear']},
        {'C': [1, 10, 100, 110, 120], 'gamma': np.logspace(-5, 1, 6), 'kernel': ['rbf']},
        {'C': [1, 10, 100, 120, 150], 'gamma': np.logspace(-10, 1, 10), 'kernel': ['sigmoid']},
        {'C': [1, 10, 100, 120, 150], 'degree': [2,3,4,5], 'kernel': ['poly']},
    ]
    
    dt_param_grid = [
        {'criterion' : ['gini','entropy'], 'max_depth' : [10, 100, 1000, 10000, None]},
        {'criterion' : ['gini','entropy'], 'max_leaf_nodes' : [10, 100, 1000, 10000, None]}
    ]

    rf_param_grid = [
        {'n_estimators' : [5,8,10,12,15], 'criterion' : ['gini','entropy'], 'max_depth' : [10, 100, 1000, 10000, None]},
        {'n_estimators' : [5,8,10,12,15], 'criterion' : ['gini','entropy'], 'max_leaf_nodes' : [10, 100, 1000, 10000, None]}
    ]    
    
    
    print 'here0'
  #  clf =  tree.DecisionTreeClassifier()
  #  clf = svm.SVC(kernel = 'rbf', C=2.2, gamma=0.001)
  #  clf = GridSearchCV(estimator=svm.SVC(), param_grid=svm_param_grid, n_jobs=16,verbose=100)
  #  clf = GridSearchCV(estimator=tree.DecisionTreeClassifier(), param_grid=dt_param_grid, n_jobs=16,verbose=100)
  #  clf = GridSearchCV(estimator=RandomForestClassifier(), param_grid=rf_param_grid, n_jobs=16,verbose=100)
  #  clf.fit(train_x, train_y)
  #  print 'here0.5'
  #  print clf.score(train_x, train_y)
  #  print clf.best_estimator_





    logging.info('start to test model...')
    test_reader = open('weibo_test_xy.txt', 'r')
    test_data = []
    comment_id = []
    test_y = []
    test_y1 = []
    test_y2 = []
    for instance in test_reader:
        instance = instance.split()
        comment_id.append(int(instance[0]))
        test_y.append(int(instance[1]))
        test_y1.append(int(instance[2]))
        test_y2.append(int(instance[3]))
        x = {'BIAS':1}
        for feature in instance[4:]:
            pos = feature.rfind(':')
            key, val = feature[:pos], feature[pos + 1:]
            if x.has_key(key):
                x[key] += float(val)
            else:
                x[key] = float(val)
        test_data.append(x)
    print 'here1'
    logging.info('Total number of instance: ' + str(len(test_data)))
    test_x = test_data
    test_x = vectorizer.transform(test_x)
 #   pred_y = clf.predict(test_x)
 #   print clf.score(test_x, test_y)
    
    
    train_y_for_each_clf = [0]
    for i in range(1,8):
        tempy_1 = array(array(Y1) == i, dtype='int32')
        tempy_2 = array(array(Y2) == i, dtype='int32')
        tempy_1 += tempy_2
        tempy_1 = array(tempy_1 != 0, dtype='int32')
        train_y_for_each_clf.append(tempy_1)


    test_y_for_each_clf = [0]
    for i in range(1,8):
        tempy_1 = array(array(test_y1) == i, dtype='int32')
        tempy_2 = array(array(test_y2) == i, dtype='int32')
        tempy_1 += tempy_2
        tempy_1 = array(tempy_1 != 0, dtype='int32')    
        test_y_for_each_clf.append(tempy_1)
    
    #method 1
    if method1 == True:  
        print 'method1' 
        nb = [0]
        if first_run == True:
            for i in range(1,8):
                nb.append(tree.DecisionTreeClassifier())
                nb[i].fit(train_x, train_y_for_each_clf[i])
                f = open('clf/dtc1%d.pickle' % i, 'wb')
                cPickle.dump(nb[i], f) 
                f.close()
        #        print nb[i].score(train_x, train_y_for_each_clf[i])
            #train_result = []
            #for i in range(1,8):
            #    train_result.append(nb[i].predict_proba(train_x).transpose()[1].tolist())
            #train_result = array(train_result)
            #train_result = train_result.transpose()
            #assert len(train_result) == 14000
            #assert len(train_result[0]) == 7
            #np.savetxt("train_result.txt",train_result,fmt='%0.2f')
        else:
            for i in range(1,8):
                nb.append(joblib.load('clf/dtc1%d.pickle' % i, 'r'))
                print nb[i].score(train_x, train_y_for_each_clf[i])
        
        
        
                

        
        print 'begin test:'
        
        for i in range(1,8):           
            print nb[i].score(test_x, test_y_for_each_clf[i])
        
        test_result = []
        for i in range(1,8):
            test_result.append(nb[i].predict_proba(test_x).transpose()[1].tolist())
        test_result = array(test_result)
        test_result = test_result.transpose()
      #  assert len(test_result) == 6000
     #   assert len(test_result[0]) == 7
        result_sort = test_result.argsort()
        first = result_sort.transpose()[-1]
        second = result_sort.transpose()[-2]
        test_pred_y1 = []
        test_pred_y2 = []
        for i in range(len(test_result)):
            if test_result[i][first[i]] >= 0.5:
                test_pred_y1.append(first[i] + 1)
                if test_result[i][second[i]] >= 0.5:
                    test_pred_y2.append(second[i] + 1)
                else:
                    test_pred_y2.append(0)
            else:
                test_pred_y1.append(0)
                test_pred_y2.append(0)
        
        result_writer = open('method1_result.txt', 'w')
        count = 0
        number_0=0
        score = 0
        for i in range(0, len(test_y2)):
            temp = 0.0
            if test_pred_y1[i] == test_y1[i] or test_pred_y1[i] == test_y2[i]:
                temp += 1.0
            if test_pred_y2[i] == test_y1[i] or test_pred_y2[i] == test_y2[i]:
                if temp == 1.0:
                    temp = 2.0
                if temp == 0:
                    temp = 0.5  
            result_writer.write("%d %d %d %d %f\n" % (test_y1[i], test_y2[i], test_pred_y1[i],test_pred_y2[i],temp / 2))  
            score += temp / 2
        result_writer.write("%f" % (score/(len(test_y2) - number_0)))
        print "method1 score:%f" % (score/(len(test_y2) - number_0))
        result_writer.flush()
        result_writer.close()
        np.savetxt("test1_proba.txt",test_result,fmt='%0.2f')
        
        #method finish
    
        #method 2
    if method2 == True:    
        print 'method2'  
        nb = [0]
        if first_run == True:
            for i in range(1,8):
                nb.append(svm.SVC(kernel = 'rbf', C=2.2, gamma=0.001, probability=True))
                nb[i].fit(train_x, train_y_for_each_clf[i])
                f = open('clf/svm2%d.pickle' % i, 'wb')
                cPickle.dump(nb[i], f) 
                f.close()
         #       print nb[i].score(train_x, train_y_for_each_clf[i])
            #train_result = []
            #for i in range(1,8):
            #    train_result.append(nb[i].predict_proba(train_x).transpose()[1].tolist())
            #train_result = array(train_result)
            #train_result = train_result.transpose()
            #assert len(train_result) == 14000
            #assert len(train_result[0]) == 7
            #np.savetxt("train_result.txt",train_result,fmt='%0.2f')
        else:
            for i in range(1,8):
                nb.append(joblib.load('clf/svm2%d.pickle' % i, 'r'))
                print nb[i].score(train_x, train_y_for_each_clf[i])
                    
        print 'begin test:'
        
        for i in range(1,8):           
            print nb[i].score(test_x, test_y_for_each_clf[i])
        
        test_result = []
        for i in range(1,8):
            test_result.append(nb[i].predict_proba(test_x).transpose()[1].tolist())
        test_result = array(test_result)
        test_result = test_result.transpose()
     #   assert len(test_result) == 6000
     #   assert len(test_result[0]) == 7
        result_sort = test_result.argsort()
        first = result_sort.transpose()[-1]
        second = result_sort.transpose()[-2]
        test_pred_y1 = []
        test_pred_y2 = []
        for i in range(len(test_result)):
            if test_result[i][first[i]] >= 0.5:
                test_pred_y1.append(first[i] + 1)
                if test_result[i][second[i]] >= 0.5:
                    test_pred_y2.append(second[i] + 1)
                else:
                    test_pred_y2.append(0)
            else:
                test_pred_y1.append(0)
                test_pred_y2.append(0)
        
        result_writer = open('method2_result.txt', 'w')
        count = 0
        number_0 = 0
        score = 0
        for i in range(0, len(test_y2)):
            temp = 0.0
            if test_pred_y1[i] == test_y1[i] or test_pred_y1[i] == test_y2[i]:
                temp += 1.0
            if test_pred_y2[i] == test_y1[i] or test_pred_y2[i] == test_y2[i]:
                if temp == 1.0:
                    temp = 2.0
                if temp == 0:
                    temp = 0.5    
            result_writer.write("%d %d %d %d %f\n" % (test_y1[i], test_y2[i], test_pred_y1[i],test_pred_y2[i],temp / 2)) 
            score += temp / 2
        result_writer.write("%f" % (score/(len(test_y2) - number_0)))
        print "method2 score:%f" % (score/(len(test_y2) - number_0))
        result_writer.flush()
        result_writer.close()
        np.savetxt("test2_proba.txt",test_result,fmt='%0.2f')
        
        #method finish
        
    if method3 == True:
        print 'method3'    
        #method 3
        nb = [0]
        if first_run == True:
            for i in range(1,8):
                nb.append(RandomForestClassifier(n_estimators=10))
                nb[i].fit(train_x, train_y_for_each_clf[i])
                f = open('clf/rf3%d.pickle' % i, 'wb')
                cPickle.dump(nb[i], f) 
                f.close()
    #            print nb[i].score(train_x, train_y_for_each_clf[i])
            #train_result = []
            #for i in range(1,8):
            #    train_result.append(nb[i].predict_proba(train_x).transpose()[1].tolist())
            #train_result = array(train_result)
            #train_result = train_result.transpose()
            #assert len(train_result) == 14000
            #assert len(train_result[0]) == 7
            #np.savetxt("train_result.txt",train_result,fmt='%0.2f')
        else:
            for i in range(1,8):
                nb.append(joblib.load('clf/rf3%d.pickle' % i, 'r'))
                print nb[i].score(train_x, train_y_for_each_clf[i])
                
        
        print 'begin test:'
        
        for i in range(1,8):           
            print nb[i].score(test_x, test_y_for_each_clf[i])
        
        test_result = []
        for i in range(1,8):
            test_result.append(nb[i].predict_proba(test_x).transpose()[1].tolist())
        test_result = array(test_result)
        test_result = test_result.transpose()
  #      assert len(test_result) == 6000
  #      assert len(test_result[0]) == 7
        result_sort = test_result.argsort()
        first = result_sort.transpose()[-1]
        second = result_sort.transpose()[-2]
        test_pred_y1 = []
        test_pred_y2 = []
        for i in range(len(test_result)):
            if test_result[i][first[i]] >= 0.5:
                test_pred_y1.append(first[i] + 1)
                if test_result[i][second[i]] >= 0.5:
                    test_pred_y2.append(second[i] + 1)
                else:
                    test_pred_y2.append(0)
            else:
                test_pred_y1.append(0)
                test_pred_y2.append(0)
        
        result_writer = open('method3_result.txt', 'w')
        count = 0
        number_0 = 0
        score = 0
        for i in range(0, len(test_y2)):
            temp = 0.0
            if test_pred_y1[i] == test_y1[i] or test_pred_y1[i] == test_y2[i]:
                temp += 1.0
            if test_pred_y2[i] == test_y1[i] or test_pred_y2[i] == test_y2[i]:
                if temp == 1.0:
                    temp = 2.0
                if temp == 0:
                    temp = 0.5  
            result_writer.write("%d %d %d %d %f\n" % (test_y1[i], test_y2[i], test_pred_y1[i],test_pred_y2[i],temp / 2)) 
            score += temp / 2
        result_writer.write("%f" % (score/(len(test_y2) - number_0)))
        print len(test_y2),number_0,len(test_y2) - number_0,score/(len(test_y2) - number_0)
        print "method3 score:%f" % (score/(len(test_y2) - number_0))
        result_writer.flush()
        result_writer.close()
        np.savetxt("test3_proba.txt",test_result,fmt='%0.2f')
        
        #method finish
        
        #还可以输出proba的有AdaBoostClassifier
    
    '''
    print 'here2'
    result_writer = open('result1.txt', 'w')
    count = 0
    for i in range(0, len(test_y)):
        result_writer.write("%d %d %d\n" % (comment_id[i], test_y[i], pred_y[i]))
    result_writer.flush()
    result_writer.close()
    print 'here3'
    '''