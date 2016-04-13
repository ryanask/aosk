'use strict';

describe('Controller Tests', function() {

    describe('RPekerjaan Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRPekerjaan;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRPekerjaan = jasmine.createSpy('MockRPekerjaan');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RPekerjaan': MockRPekerjaan
            };
            createController = function() {
                $injector.get('$controller')("RPekerjaanDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:rPekerjaanUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
