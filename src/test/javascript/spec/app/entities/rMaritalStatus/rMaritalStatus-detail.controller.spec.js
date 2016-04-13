'use strict';

describe('Controller Tests', function() {

    describe('RMaritalStatus Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRMaritalStatus;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRMaritalStatus = jasmine.createSpy('MockRMaritalStatus');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RMaritalStatus': MockRMaritalStatus
            };
            createController = function() {
                $injector.get('$controller')("RMaritalStatusDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:rMaritalStatusUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
